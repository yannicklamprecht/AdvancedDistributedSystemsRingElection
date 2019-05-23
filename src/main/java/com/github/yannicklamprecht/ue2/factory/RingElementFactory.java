package com.github.yannicklamprecht.ue2.factory;

import com.github.yannicklamprecht.ue2.messenger.network.NetworkConfiguration;
import com.github.yannicklamprecht.ue2.messenger.network.NetworkMessenger;
import com.github.yannicklamprecht.ue2.ring.NetworkRingElement;
import com.github.yannicklamprecht.ue2.ring.RingElement;
import java.io.IOException;
import java.net.ServerSocket;

/**
 * Created by ysl3000
 */
public class RingElementFactory {

    public RingElement create(int id, RingType type){

        switch (type){
            case NETWORK:
                try {
                    return new NetworkRingElement(id, new NetworkMessenger(new NetworkConfiguration(new ServerSocket(2000 + id))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            case LOCAL:
                return null;
        }
        return null;
    }

}
