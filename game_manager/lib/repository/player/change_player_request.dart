import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';

///
/// Object to request changing the password for a player.
///
class ChangePlayerRequest extends Equatable {
  final String username;
  final String password;

  ///
  /// Constructor
  ///
  const ChangePlayerRequest({
    required this.username,
    required this.password,
  });

  ///
  /// Get properties of object for comparison
  ///
  @override
  List<Object?> get props => [
    username,
    password,
  ];

  Map<String, dynamic> toJson() {
    return {
      'username': username,
      'password': password,
    };
  }

  @override
  String toString() {
    return 'ChangePasswordRequest(username: $username, password: $password)';
  }

  ///
  /// Convert object to JSON.
  ///
  JSON get asJson => {
    'username': username,
    'password': password,
  };
}
