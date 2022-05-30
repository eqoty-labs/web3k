import Foundation
import CryptoKit
import web3


@objc public class Web3Swift : NSObject {
    
    @objc public class func getAddress(privKey: String) -> String {
        let keyStorage = EthereumKeyLocalStorage()
        let account = try! EthereumAccount.importAccount(keyStorage: keyStorage, privateKey: privKey, keystorePassword: "password")
        return account.address.value
    }
    
}
