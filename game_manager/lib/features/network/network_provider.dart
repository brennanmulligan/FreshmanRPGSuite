import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:frpg_networking_api/networking/manager/network_manager.dart';
import 'package:frpg_networking_api/networking/service_client/implementations/service_client_http.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

///
/// Manages all network junk.
///
class NetworkProvider {
  ///
  /// Manage the network controller.
  ///
  static final networkController =
      StateNotifierProvider<NetworkManager, NetworkManagerState>(
    (ref) {
      return NetworkManager();
    },
  );

  ///
  /// The service client.
  ///
  static final serviceClient = Provider<ServiceClientHTTP>(
    (ref) {
      return ServiceClientHTTP(
        baseURL: ref.read(NetworkProvider.networkController).baseURL,
      );
    },
  );
}
