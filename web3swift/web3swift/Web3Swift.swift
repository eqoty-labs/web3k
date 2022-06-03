import Foundation
import web3


@objc public class Web3Swift : NSObject {
    
    
    @objc public class func importAccount(privKey: String) -> Any {
        let keyStorage = EthereumKeyLocalStorage()
        return try! EthereumAccount.importAccount(keyStorage: keyStorage, privateKey: privKey, keystorePassword: "password")
    }

    
    @objc public class func getAddress(ethereumAccount : Any) -> String? {
        return (ethereumAccount as! EthereumAccount).address.value
    }
    
}
