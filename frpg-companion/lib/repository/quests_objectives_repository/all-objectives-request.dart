import 'package:equatable/equatable.dart';

class AllObjectivesRequest extends Equatable {
  final int playerID;

  const AllObjectivesRequest({required this.playerID});

  @override
  List<Object?> get props =>  [playerID];

  Map<String, dynamic> toJson() {
    return {'playerID': playerID};
  }

  @override
  String toString() {
    return 'AllObjectivesRequest(playerID: $playerID)';
  }
}