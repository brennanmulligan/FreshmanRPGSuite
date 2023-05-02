import 'package:equatable/equatable.dart';
import 'package:game_manager/repository/player/vanity_DTO.dart';

import '../../type_definitions.dart';
import 'crew.dart';
import 'major.dart';

class Player extends Equatable {
  final int playerID;
  final String playerName;
  String? playerPassword;
  String? appearanceType = "";
  int? row = 0;
  int? column = 0;
  int? doubloons = 0;
  String? mapName = "";
  int? experiencePoints = 0;
  Crew? crew;
  Major? major;
  int? sectionID = 0;
  int? buffPool = 0;
  List<String>? visitedMaps;
  List<VanityDTO>? vanityDTOs;

  Player(
      {required this.playerID,
      required this.playerName,
      this.playerPassword,
      this.appearanceType,
      this.row,
      this.column,
      this.doubloons,
      this.mapName,
      this.experiencePoints,
      this.crew,
      this.major,
      this.sectionID,
      this.buffPool,
      this.visitedMaps,
      this.vanityDTOs});

  @override
  List<Object?> get props => [
        playerID,
        playerName,
        playerPassword,
        appearanceType,
        row,
        column,
        doubloons,
        mapName,
        experiencePoints,
        crew,
        major,
        sectionID,
        buffPool,
        visitedMaps,
        vanityDTOs
      ];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {'playerID': playerID, 'playerName': playerName};
  }

  factory Player.fromJson({
    required JSON json,
  }) {
    //other fields aren't needed yet and would add unnecessary complexity
    return Player(playerID: json['playerID'], playerName: json['playerName']);
  }
}
