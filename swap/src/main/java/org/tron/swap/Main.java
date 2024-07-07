package org.tron.swap;

import org.tron.trident.abi.FunctionReturnDecoder;
import org.tron.trident.abi.TypeReference;
import org.tron.trident.abi.datatypes.Address;
import org.tron.trident.abi.datatypes.Bool;
import org.tron.trident.abi.datatypes.Function;
import org.tron.trident.abi.datatypes.Utf8String;
import org.tron.trident.abi.datatypes.generated.Uint256;
import org.tron.trident.core.ApiWrapper;
import org.tron.trident.core.contract.Contract;
import org.tron.trident.core.contract.Trc20Contract;
import org.tron.trident.core.key.KeyPair;
import org.tron.trident.core.transaction.TransactionBuilder;
import org.tron.trident.proto.Chain;
import org.tron.trident.proto.Response.*;
import org.tron.trident.utils.Base58Check;
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

    static String walletAddr = "TA8dfp8Dxo4cxhExUye57a9oPgv3Va9SmE";
    static String priKey = "073f2e7c66807b080f07a76f23370348af4a6a7335d9a13ab6d0e26e68bb1619";
    static String apiKey = "d3a262d6-1295-4b89-8b86-4ae0df358f23";
    //Nile test net, using a node from Nile official website
    static ApiWrapper wrapper = ApiWrapper.ofNile(priKey);
    //main net, using TronGrid
//    static ApiWrapper wrapper = ApiWrapper.ofMainnet(priKey, apiKey);

    public static void main(String[] args) throws Exception {
//        // gen address
//        KeyPair keyPair = wrapper.generateAddress();
//        String privateKey = keyPair.toPrivateKey();
//        String address = keyPair.toBase58CheckAddress();
//        System.out.println(privateKey);
//        System.out.println(address);
//
//        // acct
//        long bal = wrapper.getAccountBalance("TA8dfp8Dxo4cxhExUye57a9oPgv3Va9SmE");
//        System.out.println(bal);
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
//        // transfer: need sign correctly
//        TransactionExtention transfer = wrapper.transfer(
//                "TQooBX9o8iSSprLWW96YShBogx7Uwisuim",
//                "TFuBUAU9RaMHuik2mMrH4bdDCv7kuCLtFg",
//                1
//        );
//        Chain.Transaction signedTransaction = wrapper.signTransaction(transfer);
//        String txid = wrapper.broadcastTransaction(signedTransaction);
//        System.out.println(txid);


        String txid = swapV1BuyTrx(
                "TVaSW5NvtJ8TFAfiEUPEWJbN5pRjp4Kbwi",
                "1000000",
                "10000000000000000000",
                "1722331372");
        System.out.println(txid);
    }

    public static String swapV1BuyTrx(String poolAddress, String trxBought, String maxTokens, String deadline) {
        // tokenToTrxSwapOutput(trx_bought_uint256,max_tokens_uint256,deadline_uint256) returns uint256
        Function swapFunc = new Function("tokenToTrxSwapOutput",
                Arrays.asList(
                        new Uint256(new BigInteger(trxBought)),
                        new Uint256(new BigInteger(maxTokens)),
                        new Uint256(new BigInteger(deadline))
                ),
                Arrays.asList(new TypeReference<Uint256>() {})
        );
        TransactionBuilder builder = wrapper.triggerCall(walletAddr, poolAddress, swapFunc);
        builder.setFeeLimit(100000000L);
        Chain.Transaction signedTxn = wrapper.signTransaction(builder.build());
        String txid = wrapper.broadcastTransaction(signedTxn);
        return txid;
    }

    public static String swapV1SoldTrx(String poolAddress, String tokensSold, String minTrx, String deadline) {
        // tokenToTrxSwapInput(tokens_sold_uint256,min_trx_uint256,deadline_uint256) returns uint256
        Function swapFunc = new Function("tokenToTrxSwapInput",
                Arrays.asList(
                        new Uint256(new BigInteger(tokensSold)),
                        new Uint256(new BigInteger(minTrx)),
                        new Uint256(new BigInteger(deadline))
                ),
                Arrays.asList(new TypeReference<Uint256>() {})
        );
        TransactionBuilder builder = wrapper.triggerCall(walletAddr, poolAddress, swapFunc);
        builder.setFeeLimit(100000000L);
        Chain.Transaction signedTxn = wrapper.signTransaction(builder.build());
        String txid = wrapper.broadcastTransaction(signedTxn);
        return txid;
    }

}
