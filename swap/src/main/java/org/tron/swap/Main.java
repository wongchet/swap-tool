package org.tron.swap;

import org.tron.trident.core.ApiWrapper;

/**
 * @author: Chet Wong
 * @date: 2024/7/5
 * @description:
 */
public class Main {

    public static void main(String[] args) {
        //main net, using TronGrid
        ApiWrapper wrapper = ApiWrapper.ofMainnet(
                "073f2e7c66807b080f07a76f23370348af4a6a7335d9a13ab6d0e26e68bb1619",
                "d3a262d6-1295-4b89-8b86-4ae0df358f23");
        long bal = wrapper.getAccountBalance("TQooBX9o8iSSprLWW96YShBogx7Uwisuim");
        System.out.println(bal);
        //Shasta test net, using TronGrid
//    ApiWrapper wrapper = ApiWrapper.ofShasta("hex private key");
        //Nile test net, using a node from Nile official website
//    ApiWrapper wrapper = ApiWrapper.ofNile("hex private key");
    }

}
