import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';

///
/// Object to response for completing login.
///
class LogoutResponse extends Equatable {
  final int playerID;
  final bool success;

  ///
  /// Constructor.
  ///
  const LogoutResponse({
    required this.playerID, required this.success
  });

  ///
  /// Factory mapping `JSON` to `LoginWithCredentialsResponse`.
  ///
  factory LogoutResponse.fromJson({
    required JSON json,
  }) {
    return LogoutResponse(
        playerID: json['playerID'], success: json['success']
    );
  }

  ///
  /// Get properties of object for comparison.
  ///
  @override
  List<Object?> get props => [
    playerID,
  ];

  ///
  /// Convert object to string.
  ///
  @override
  String toString() {
    return 'LogoutResponse(playerID: $playerID)';
  }
}
