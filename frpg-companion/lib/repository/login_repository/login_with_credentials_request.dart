import 'package:equatable/equatable.dart';


///
/// Object to request for logging in with credentials.
///
class LoginWithCredentialsRequest extends Equatable {
  final String playerName;
  final String password;

  ///
  /// Constructor
  ///
  const LoginWithCredentialsRequest(
      {required this.playerName, required this.password});

  ///
  /// Converting object to string
  ///
  @override
  String toString() {
    return 'LoginWithCredentialsRequest(playerName: $playerName, '
        'password: $password)';
  }

  ///
  /// Get properties for comparison
  ///
  @override
  List<Object?> get props => [playerName, password];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {'playerName': playerName, 'password': password};
  }
}
