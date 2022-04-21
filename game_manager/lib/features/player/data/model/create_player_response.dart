import 'package:equatable/equatable.dart';
import 'package:frpg_networking_api/networking/service_client/type_definitions/type_definitions.dart';

import 'player_response_type.dart';
///
/// Object to request creating a player.
///
class CreatePlayerResponse extends Equatable {
  final PlayerResponseType responseType;

  ///
  /// Constructor
  ///
  const CreatePlayerResponse({
    required this.responseType,
  });

  factory CreatePlayerResponse.fromJson({
    required JSON json,
  }) {
    return CreatePlayerResponse(
      responseType: PlayerResponseType.values[json['responseType']],
    );
  }

  ///
  /// Get properties of object for comparison
  ///
  @override
  List<Object?> get props => [
    responseType,
  ];
  
  @override
  String toString() {
    return 'CreatePlayerResponse(responseType: $responseType)';
  }
}