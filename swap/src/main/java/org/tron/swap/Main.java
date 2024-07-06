package org.tron.swap;

import org.tron.trident.abi.FunctionReturnDecoder;
import org.tron.trident.abi.TypeReference;
import org.tron.trident.abi.datatypes.Function;
import org.tron.trident.abi.datatypes.Utf8String;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.contract.Contract;
import org.tron.trident.core.contract.Trc20Contract;
import org.tron.trident.proto.Response.*;
import org.tron.trident.utils.Numeric;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author: Chet Wong
 * @date: 2024/7/5
 * @description:
 */
public class Main {

    //Shasta test net, using TronGrid
//        ApiWrapper wrapper = ApiWrapper.ofShasta("hex private key");
    //Nile test net, using a node from Nile official website
//        ApiWrapper wrapper = ApiWrapper.ofNile("hex private key");
    //main net, using TronGrid
    static ApiWrapper wrapper = ApiWrapper.ofMainnet(
            "073f2e7c66807b080f07a76f23370348af4a6a7335d9a13ab6d0e26e68bb1619",
            "d3a262d6-1295-4b89-8b86-4ae0df358f23"
    );

    public static void main(String[] args) throws Exception {
        // acct
        long bal = wrapper.getAccountBalance("TQooBX9o8iSSprLWW96YShBogx7Uwisuim");
        System.out.println(bal);

        // transaction
        TransactionSignWeight transaction = wrapper.getTransactionSignWeight(
                wrapper.getTransactionById("fd15cc89a6533faba4673f151f1f443f5939ad2e8c9800e767d44e99148f0ad3"));
        System.out.println(transaction);

        // trc20
        Contract contract = wrapper.getContract("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
        Trc20Contract token = new Trc20Contract(contract, "TQooBX9o8iSSprLWW96YShBogx7Uwisuim", wrapper);
        BigInteger supply = token.totalSupply();
        System.out.println(supply);

        // call func
        Function name = new Function(
                "name",
                Collections.emptyList(),
                Arrays.asList(new TypeReference<Utf8String>() {})
        );
        TransactionExtention txnExt = wrapper.constantCall(
                "TQooBX9o8iSSprLWW96YShBogx7Uwisuim",
                "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t",
                name
        );
        String result = Numeric.toHexString(txnExt.getConstantResult(0).toByteArray());
        String value = (String) FunctionReturnDecoder.decode(result, name.getOutputParameters()).get(0).getValue();
        System.out.println(value);
    }


}
