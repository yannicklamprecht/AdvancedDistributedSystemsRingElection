package com.github.yannicklamprecht.ue2.messenger.network;

import com.github.yannicklamprecht.ue2.message.Message;
import com.github.yannicklamprecht.ue2.messenger.Messenger;
import java.io.IOException;
import java.util.Optional;

/**
 * Created by ysl3000
 */
public class NetworkMessenger implements Messenger<NetworkConfiguration,Message> {

    private NetworkConfiguration networkConfiguration;

    public NetworkMessenger(NetworkConfiguration networkConfiguration) {
        this.networkConfiguration = networkConfiguration;
    }

    @Override
    public Message read() {
        try {
            return (Message) networkConfiguration.getIn().readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void write(Message message) {
        networkConfiguration.getOut().ifPresent(out -> {
            try {
                out.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public Optional<NetworkConfiguration> getConfigurator() {
        return Optional.of(networkConfiguration);
    }

    @Override
    public boolean init() {
        return networkConfiguration.init();
    }
}
