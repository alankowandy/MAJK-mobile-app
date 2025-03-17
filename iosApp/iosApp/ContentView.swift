import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ZStack{
            Color(UIColor(_colorLiteralRed: 247/255, green: 247/255, blue: 247/255
                          , alpha: 1)).ignoresSafeArea()
            ComposeView()
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
            
        }
    }
}



