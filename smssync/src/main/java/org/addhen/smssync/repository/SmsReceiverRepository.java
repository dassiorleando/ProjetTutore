package org.addhen.smssync.repository;

import org.addhen.smssync.domains.SmsReceiverP;

/**
 * Created by dassi on on 06/08/15.
 */
public class SmsReceiverRepository extends BaseRemoteQuery<SmsReceiverP> {

    public SmsReceiverRepository() {
        super(SmsReceiverP.class);
    }

}
