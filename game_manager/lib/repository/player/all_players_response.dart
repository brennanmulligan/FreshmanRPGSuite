import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';

class AllPlayersResponse extends Equatable {
  final bool success;
  final List<String> players;

  const AllPlayersResponse(this.success, {required this.players});

  const AllPlayersResponse.allFields({required this.success, required this.players});

  factory AllPlayersResponse.fromJson({
    required JSON json,
  }) {
    return AllPlayersResponse.allFields(
        success: json['success'],
        players: (json['players'] as List<String>)
    );
  }

  @override
  List<Object?> get props => [success, players];

  @override
  String toString() {
    return 'AllPlayersResponse(Players: $players)';
  }
}