package org.zstack.test.compute.vm;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.zstack.core.cloudbus.CloudBus;
import org.zstack.core.componentloader.ComponentLoader;
import org.zstack.core.db.DatabaseFacade;
import org.zstack.header.vm.VmAccountPerference;
import org.zstack.header.vm.VmInstanceInventory;
import org.zstack.header.vm.VmInstanceState;
import org.zstack.test.Api;
import org.zstack.test.ApiSenderException;
import org.zstack.test.DBUtil;
import org.zstack.test.deployer.Deployer;

/**
 * Created by mingjian.deng on 16/10/19.
 */
public class TestChangeVmPassword {
    Deployer deployer;
    Api api;
    ComponentLoader loader;
    CloudBus bus;
    DatabaseFacade dbf;

    @Before
    public void setUp() throws Exception {
        DBUtil.reDeployDB();
        deployer = new Deployer("deployerXml/vm/TestChangeVmPassword.xml");
        deployer.build();
        api = deployer.getApi();
        loader = deployer.getComponentLoader();
        bus = loader.getComponent(CloudBus.class);
        dbf = loader.getComponent(DatabaseFacade.class);
    }

    @Test
    public void test() throws ApiSenderException {
        VmInstanceInventory inv = api.listVmInstances(null).get(0);
        Assert.assertEquals(VmInstanceState.Running.toString(), inv.getState());

        VmAccountPerference account = api.changeVmPassword(new VmAccountPerference(
                inv.getUuid(), "root", "test1234"));
        Assert.assertNotNull(account);
        Assert.assertEquals(inv.getUuid(), account.getVmUuid());
        Assert.assertEquals("root", account.getUserAccount());
        Assert.assertEquals("test1234", account.getAccountPassword());

        // password can't contain the characters which not in A-Z,a-z,0-9,_
        try {
            account = api.changeVmPassword(new VmAccountPerference(
                    inv.getUuid(), "root", "||||||"));
        } catch(ApiSenderException e){
            Assert.assertTrue(e.getMessage().indexOf("valid regex value") != -1);
        }
        Assert.assertEquals(inv.getUuid(), account.getVmUuid());
        Assert.assertEquals("root", account.getUserAccount());
        Assert.assertEquals("test1234", account.getAccountPassword());
    }
}
