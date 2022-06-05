import Foundation
import web3
import BigInt



@objc public class Web3Swift : NSObject {
    
    
    @objc public enum ABIType: Int, RawRepresentable {
        case address
        case bool
        case string
        case uint256
        case int256
        case uint64
        case uint32
        case uint16
        case uint8
        case addressArray
        case boolArray
        case stringArray
        case uint256Array
        case int256Array
        case uint64Array
        case uint32Array
        case uint16Array
        case uint8Array
    }
    
    @objc public class func dummy(type: ABIType) -> String {
        return "This is just a dummy function to expose ABIType to kotlin"
    }
    
    @objc public class func importAccount(privKey: String) -> Any {
        let keyStorage = EthereumKeyLocalStorage()
        return try! EthereumAccount.importAccount(keyStorage: keyStorage, privateKey: privKey, keystorePassword: "password")
    }
    
    
    @objc public class func getAddress(ethereumAccount : Any) -> String? {
        return (ethereumAccount as! EthereumAccount).address.value
    }
    
    
    @objc public class func createClient(url : String) -> Any {
        return EthereumClient(url: URL(string: url)!) as Any
    }
    
    @objc public class func estimateGas(
        ethereumClient: Any,
        ethereumAccount: Any,
        contractAddress: String,
        functionName: String,
        functionParamsData: [Any],
        functionParamsTypes: [Int],
        sendValue: String?,
        onComplete: @escaping (Error?, String?) -> Void
    ) {
        let ethereumClient : EthereumClient = ethereumClient as! EthereumClient
        let ethereumAccount : EthereumAccount = ethereumAccount as! EthereumAccount
        let functionParams = zip(
            functionParamsData,
            functionParamsTypes.map{ rawType in Web3Swift.ABIType.init(rawValue: rawType)! }
        ).map{$0}
        let contractAddress = EthereumAddress(contractAddress)
        
        let function = DynamicFunction(
            name: functionName,
            contract: contractAddress,
            from: ethereumAccount.address,
            functionParams: functionParams
        )
        let sendValueBigInt: BigUInt?
        if sendValue != nil {
            sendValueBigInt = BigUInt(hex: sendValue!)
        } else {
            sendValueBigInt = nil
        }
        // todo remove gasLimit after https://github.com/argentlabs/web3.swift/issues/224 is resolved
        let transaction = try! function.transaction(value: sendValueBigInt, gasLimit: BigUInt.init("10000000")!)
        
        ethereumClient.eth_estimateGas(
            transaction,
            withAccount: ethereumAccount,
            completion: { error, estimate in
                onComplete(error, estimate?.web3.hexString)
            }
        )
    }
    
    @objc public class func sendRawTransaction(
        ethereumClient: Any,
        ethereumAccount: Any,
        contractAddress: String,
        functionName: String,
        functionParamsData: [Any],
        functionParamsTypes: [Int],
        gasLimit: String,
        sendValue: String?,
        onComplete: @escaping (Error?, String?, Int) -> Void
    ) {
        let ethereumClient : EthereumClient = ethereumClient as! EthereumClient
        let ethereumAccount : EthereumAccount = ethereumAccount as! EthereumAccount
        let functionParams = zip(
            functionParamsData,
            functionParamsTypes.map{ rawType in Web3Swift.ABIType.init(rawValue: rawType)! }
        ).map{$0}
        let contractAddress = EthereumAddress(contractAddress)
        let function = DynamicFunction(
            name: functionName,
            contract: contractAddress,
            from: ethereumAccount.address,
            functionParams: functionParams
        )
        let sendValueBigInt: BigUInt?
        if sendValue != nil {
            sendValueBigInt = BigUInt(hex: sendValue!)
        } else {
            sendValueBigInt = nil
        }
        let transaction = try! function.transaction(value: sendValueBigInt, gasLimit: BigUInt(hex: gasLimit)!)
        
        
        ethereumClient.eth_sendRawTransaction(transaction,
                                              withAccount: ethereumAccount,
                                              completion: { error, result in onComplete(error, result, transaction.nonce ?? 0) }
        )
    }
    
}




private struct DynamicFunction: ABIFunction {
    public static var name = ""
    public let gasPrice: BigUInt? = nil
    public let gasLimit: BigUInt? = nil
    public var contract: EthereumAddress
    public let from: EthereumAddress?
    public let functionParams: [(Any, Web3Swift.ABIType)]
    
    
    public init(
        name: String,
        contract: EthereumAddress,
        from: EthereumAddress,
        functionParams: [(Any, Web3Swift.ABIType)]
    ) {
        DynamicFunction.name = name
        self.contract = contract
        self.from = from
        self.functionParams = functionParams
    }
    
    public func encode(to encoder: ABIFunctionEncoder) throws {
        functionParams.forEach{ functionParam in
            encode(encoder, functionParam)
        }
    }
    
    private func encode(_ encoder: ABIFunctionEncoder, _ paramAndType: (Any, Web3Swift.ABIType)) {
        let param = paramAndType.0
        let type = paramAndType.1
        print("encode paramAndType: \(param) \(type)")
        
        switch type {
        case .address:
            try! encoder.encode(EthereumAddress(param as! String))
        case .bool:
            try! encoder.encode(param as! Bool)
        case .string:
            try! encoder.encode(param as! String)
        case .uint256:
            try! encoder.encode(BigUInt.init(hex: param as! String)!)
        case .int256:
            try! encoder.encode(BigInt.init(hex: param as! String)!)
        case .uint64:
            try! encoder.encode(UInt64(param as! Int))
        case .uint32:
            try! encoder.encode(UInt32(param as! Int))
        case .uint16:
            try! encoder.encode(UInt16(param as! Int))
        case .uint8:
            try! encoder.encode(UInt8(param as! Int))
        case .addressArray:
            try! encoder.encode((param as! [String]).map{ EthereumAddress($0) })
        case .boolArray:
            try! encoder.encode((param as! [Bool]))
        case .stringArray:
            try! encoder.encode(param as! [String])
        case .uint256Array:
            try! encoder.encode((param as! [String]).map{ BigUInt.init(hex: $0)! })
        case .int256Array:
            try! encoder.encode((param as! [String]).map{ BigInt.init(hex: $0)! })
        case .uint64Array:
            try! encoder.encode((param as! [Int]).map{ UInt64($0) })
        case .uint32Array:
            try! encoder.encode((param as! [Int]).map{ UInt32($0) })
        case .uint16Array:
            try! encoder.encode((param as! [Int]).map{ UInt16($0) })
        case .uint8Array:
            try! encoder.encode((param as! [Int]).map{ UInt8($0) })
        }
    }
}
