package org.zstack.header.volume;

import org.zstack.header.core.ApiTimeout;
import org.zstack.header.message.OverlayMessage;
import org.zstack.header.vm.APIChangeVMPasswordMsg;

/**
 * Created by mingjian.deng on 16/10/27.
 */
@ApiTimeout(apiClasses = {APIChangeVMPasswordMsg.class})
public class MigrateHostOverlayVolumeMsg extends OverlayMessage implements VolumeMessage {
    private String volumeUuid;

    @Override
    public String getVolumeUuid() {
        return volumeUuid;
    }

    public void setVolumeUuid(String volumeUuid) {
        this.volumeUuid = volumeUuid;
    }


}
