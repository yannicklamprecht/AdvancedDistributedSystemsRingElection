package com.github.yannicklamprecht.ue2.factory;

import com.github.yannicklamprecht.ue2.messenger.RingElement;
import com.github.yannicklamprecht.ue2.messenger.configuration.local.LocalConfiguration;
import com.github.yannicklamprecht.ue2.messenger.configuration.network.NetworkConfiguration;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by ysl3000
 */
public class RingElementFactory {

    public RingElement create(int id, RingType type) {

        switch (type) {
            case NETWORK:
                try {
                    return new RingElement<>(id, new NetworkConfiguration(new ServerSocket(2000 + id)));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case LOCAL:
                return new RingElement<>(id, new LocalConfiguration());
        }
        return null;
    }

}
