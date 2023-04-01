import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';

///
/// Object to request creating a player.
///

class CreatePlayerWithNameResponse extends Equatable{
  final bool success;
  final String description;
  final String playerName;

  const CreatePlayerWithNameResponse({
    required this.success,
    required this.description,
     required this.playerName
  });

  factory CreatePlayerWithNameResponse.fromJson({
    required JSON json,
  }) {
    return CreatePlayerWithNameResponse(
        success: json['success'],
        description: json['description'],
        playerName: json['playerName']
    );
  }
  @override
  List<Object?> get props => [
    success, description, playerName
  ];

  @override
  String toString() {
    return 'CreatePlayerWithNameResponse(success: $success, description: $description, playerName: $playerName)';
  }
}

class CreateManyPlayersResponse extends Equatable {
  final bool success;
  final String description;
  final List<CreatePlayerWithNameResponse> successful;
  final List<CreatePlayerWithNameResponse> failed;

  ///
  /// Constructor
  ///
  const CreateManyPlayersResponse({
    required this.success,
    required this.description,
    required this.successful,
    required this.failed
  });

  factory CreateManyPlayersResponse.fromJson({
    required JSON json,
  }) {
    return CreateManyPlayersResponse(
        success: json['success'],
        description: json['description'],
        successful: (json['CreatePlayerWithNameResponse'] as List)
            .map((e) => CreatePlayerWithNameResponse.fromJson(json: e))
            .toList(),
        failed: (json['CreatePlayerWithNameResponse'] as List)
        .map((e) => CreatePlayerWithNameResponse.fromJson(json: e))
        .toList()
    );
  }

  ///
  /// Get properties of object for comparison
  ///
  @override
  List<Object?> get props => [
    success, description, successful, failed
  ];

  @override
  String toString() {
    return 'CreatePlayerResponse(success: $success, description: $description, successful: $successful, failed: $failed)';
  }
}