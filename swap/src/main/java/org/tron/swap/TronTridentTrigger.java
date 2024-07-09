package org.tron.swap;

import com.google.common.collect.Lists;
import com.google.protobuf.ByteString;
import org.apache.tomcat.util.buf.HexUtils;
import org.tron.trident.abi.TypeReference;
import org.tron.trident.abi.datatypes.*;
import org.tron.trident.abi.datatypes.generated.Uint24;
import org.tron.trident.abi.datatypes.generated.Uint256;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.transaction.TransactionBuilder;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Response;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

/**
 * @author: Chet Wong
 * @date: 2024/7/5
 * @description:
 */
public class TronTridentTrigger {

    static String walletAddr = "TA8dfp8Dxo4cxhExUye57a9oPgv3Va9SmE";
    static String priKey = "073f2e7c66807b080f07a76f23370348af4a6a7335d9a13ab6d0e26e68bb1619";
    static String apiKey = "d3a262d6-1295-4b89-8b86-4ae0df358f23";
    //Nile test net, using a node from Nile official website
    static ApiWrapper wrapper = ApiWrapper.ofNile(priKey);
    //main net, using TronGrid
//    static ApiWrapper wrapper = ApiWrapper.ofMainnet(priKey, apiKey);


    static String routerAddress = "TB6xBCixqRPUSKiXb45ky1GhChFJ7qrfFj";

    public static void main(String[] args) throws Exception {
//        // gen address
//        KeyPair keyPair = wrapper.generateAddress();
//        String privateKey = keyPair.toPrivateKey();
//        String address = keyPair.toBase58CheckAddress();
//        System.out.println(privateKey);
//        System.out.println(address);
//
        // acct
        long bal = wrapper.getAccountBalance("TA8dfp8Dxo4cxhExUye57a9oPgv3Va9SmE");
        System.out.println(bal);
//
//        // transaction
//        TransactionSignWeight transaction = wrapper.getTransactionSignWeight(
//                wrapper.getTransactionById("fd15cc89a6533faba4673f151f1f443f5939ad2e8c9800e767d44e99148f0ad3"));
//        System.out.println(transaction);
//
//        // trc20
//        Contract contract = wrapper.getContract("TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t");
//        Trc20Contract token = new Trc20Contract(contract, "TQooBX9o8iSSprLWW96YShBogx7Uwisuim", wrapper);
//        BigInteger supply = token.totalSupply();
//        System.out.println(supply);
//
//        // call func
//        Function name = new Function(
//                "name",
//                Collections.emptyList(),
//                Arrays.asList(new TypeReference<Utf8String>() {})
//        );
//        TransactionExtention txnExt = wrapper.constantCall(
//                "TQooBX9o8iSSprLWW96YShBogx7Uwisuim",
//                "TR7NHqjeKQxGTCi8q8ZY4pL8otSzgjLj6t",
//                name
//        );
//        String result = Numeric.toHexString(txnExt.getConstantResult(0).toByteArray());
//        String value = (String) FunctionReturnDecoder.decode(result, name.getOutputParameters()).get(0).getValue();
//        System.out.println(value);
//
        // transfer: need sign correctly
//        TransactionExtention transfer = wrapper.transfer(
//                "TQooBX9o8iSSprLWW96YShBogx7Uwisuim",
//                "TFuBUAU9RaMHuik2mMrH4bdDCv7kuCLtFg",
//                1
//        );
//        Chain.Transaction signedTransaction = wrapper.signTransaction(transfer);
//        String txid = wrapper.broadcastTransaction(signedTransaction);
//        System.out.println(txid);


//        String txid = swapV1BuyTrx(
//                "TVaSW5NvtJ8TFAfiEUPEWJbN5pRjp4Kbwi",
//                "1000000",
//                "10000000000000000000",
//                "1722331372");
//        System.out.println(txid);

//        swapRouter();
//        swapRouterTRX();

    }

    public static class RouterParamSwapData extends StaticStruct {
        public BigInteger amountIn;
        public BigInteger amountOutMin;
        public String to;
        public BigInteger deadline;

        public RouterParamSwapData(BigInteger amountIn, BigInteger amountOutMin, String to, BigInteger deadline) {
            super(new Uint256(amountIn),
                    new Uint256(amountOutMin),
                    new Address(to),
                    new Uint256(deadline));
            this.amountIn = amountIn;
            this.amountOutMin = amountOutMin;
            this.to = to;
            this.deadline = deadline;
        }

        public RouterParamSwapData(Uint256 amountIn, Uint256 amountOutMin, Address to, Uint256 deadline) {
            super(amountIn, amountOutMin, to, deadline);
            this.amountIn = amountIn.getValue();
            this.amountOutMin = amountOutMin.getValue();
            this.to = to.getValue();
            this.deadline = deadline.getValue();
        }
    }

    public static String swapRouter() {
        // swapExactInput(address[],string[],uint256[],uint24[],(uint256,uint256,address,uint256))
        // [path, poolVersion, versionLen, fees, [amountIn, amountOut, to, deadline]]
        // jst -> sun -> wtrx -> trx
        Function swapFunc = new Function("swapExactInput",
                Arrays.asList(
                        new DynamicArray<>(Address.class, new Address("TF17BgPaZYbz8oxbjhriubPDsA7ArKoLX3"), new Address("TWrZRHY9aKQZcyjpovdH6qeCEyYZrRQDZt"), new Address("TYsbWxNnyTgsZaTFaue9hqpxkU3Fkco94a"), new Address("T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb")),
                        new DynamicArray<>(Utf8String.class, new Utf8String("v2")),
                        new DynamicArray<>(Uint256.class, new Uint256(new BigInteger("4"))),
                        new DynamicArray<>(Uint24.class, new Uint24(new BigInteger("0")), new Uint24(new BigInteger("0")), new Uint24(new BigInteger("0")), new Uint24(new BigInteger("0"))),
                        new RouterParamSwapData(
                                new BigInteger("11000000000000000000"),
                                new BigInteger("3915071972"),
                                walletAddr,
                                BigInteger.valueOf(new Date().getTime() / 1000 + 86400))
                ),
                Arrays.asList(new TypeReference<Uint256>() {
                })
        );
        TransactionBuilder builder = wrapper.triggerCall(walletAddr, routerAddress, swapFunc);
//        System.out.println(HexUtils.toHexString(builder.getTransaction().getRawData().getContract(0).getParameter().getValue().toByteArray()));
        builder.setFeeLimit(500 * 1000000);
        Chain.Transaction signedTxn = wrapper.signTransaction(builder.build());
        String txid = wrapper.broadcastTransaction(signedTxn);
        System.out.println(txid);
        return txid;
    }

    public static String swapRouterTRX() {
        // swapExactInput(address[],string[],uint256[],uint24[],(uint256,uint256,address,uint256))
        // [path, poolVersion, versionLen, fees, [amountIn, amountOut, to, deadline]]
        // trx -> usdt
        Function swapFunc = new Function("swapExactInput",
                Arrays.asList(
                        new DynamicArray<>(Address.class, new Address("T9yD14Nj9j7xAB4dbGeiX9h8unkKHxuWwb"), new Address("TXYZopYRdj2D9XRtbG411XZZ3kM5VkAeBf")),
                        new DynamicArray<>(Utf8String.class, new Utf8String("v1")),
                        new DynamicArray<>(Uint256.class, new Uint256(new BigInteger("2"))),
                        new DynamicArray<>(Uint24.class, new Uint24(new BigInteger("0")), new Uint24(new BigInteger("0"))),
                        new RouterParamSwapData(
                                new BigInteger("111000000"),
                                new BigInteger("7880043169670"),
                                walletAddr,
                                new BigInteger("1720506621"))
                ),
                Arrays.asList(new TypeReference<Uint256>() {
                })
        );
        TransactionBuilder builder = wrapper.triggerCallWithValue(walletAddr, routerAddress, swapFunc, 111000000);
//        System.out.println(HexUtils.toHexString(builder.getTransaction().getRawData().getContract(0).getParameter().getValue().toByteArray()));
        builder.setFeeLimit(500 * 1000000);
        Chain.Transaction signedTxn = wrapper.signTransaction(builder.build());
        String txid = wrapper.broadcastTransaction(signedTxn);
        System.out.println(txid);
        return txid;
    }

}
