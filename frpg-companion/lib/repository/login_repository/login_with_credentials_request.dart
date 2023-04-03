import 'package:equatable/equatable.dart';


///
/// Object to request for logging in with credentials.
///
class LoginWithCredentialsRequest extends Equatable {
  final String username;
  final String password;

  ///
  /// Constructor
  ///
  const LoginWithCredentialsRequest(
      {required this.username, required this.password});

  ///
  /// Converting object to string
  ///
  @override
  String toString() {
    return 'LoginWithCredentialsRequest(username: $username, '
        'password: $password)';
  }

  ///
  /// Get properties for comparison
  ///
  @override
  List<Object?> get props => [username, password];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {'username': username, 'password': password};
  }
}