package org.zstack.header.configuration;

import org.zstack.header.message.APIListMessage;

import java.util.List;

public class APIListDiskOfferingMsg extends APIListMessage {
    public APIListDiskOfferingMsg(List<String> uuids) {
        super(uuids);
    }
    
    public APIListDiskOfferingMsg() {
    }
}
