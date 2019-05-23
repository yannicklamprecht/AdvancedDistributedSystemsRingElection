package com.github.yannicklamprecht.ue2.ring;

import com.github.yannicklamprecht.ue2.message.Message;
import com.github.yannicklamprecht.ue2.messenger.Messenger;
import com.github.yannicklamprecht.ue2.messenger.network.NetworkConfiguration;

/**
 * Created by ysl3000
 */
public class NetworkRingElement extends RingElement<NetworkConfiguration> {

    public NetworkRingElement(int id, Messenger<NetworkConfiguration, Message> messenger) {
        super(id, messenger);
    }

    @Override
    public void connectNextRingElement(RingElement<NetworkConfiguration> ringElement) {
        ringElement.getMessenger().getConfigurator().ifPresent(this::connectToNextMessenger);
    }
}
