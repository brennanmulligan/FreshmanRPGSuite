import 'package:equatable/equatable.dart';
import 'package:frpg_companion/features/network/network.dart';

///
/// Object to request completing an object.
///
class CompleteObjectiveRequest extends Equatable {
  final num playerID;
  final num objectiveID;
  final num questID;
  final num latitude;
  final num longitude;

  ///
  /// Constructor.
  ///
  const CompleteObjectiveRequest({
    required this.playerID,
    required this.objectiveID,
    required this.questID,
    this.latitude = 0,
    this.longitude = 0,
  });

  ///
  /// Convert object to string.
  ///
  @override
  String toString() {
    return 'CompleteObjectiveRequest(playerID: $playerID, '
        'objectiveID: $objectiveID, questID: $questID, '
        'latitude: $latitude, longitude: $longitude)';
  }

  ///
  /// Get properties for comparisons.
  ///
  @override
  List<Object?> get props => [
        playerID,
        objectiveID,
        questID,
        latitude,
        longitude,
      ];

  ///
  /// Convert object to JSON.
  ///
  JSON get asJson => {
        'playerID': playerID,
        'objectiveID': objectiveID,
        'questID': questID,
        'latitude': latitude,
        'longitude': longitude
      };
}
