import 'package:equatable/equatable.dart';
import 'package:frpg_companion/features/network/network.dart';

class FetchAllObjectiveRequest extends Equatable {
  final num playerID;

  ///
  /// Constructor
  ///
  const FetchAllObjectiveRequest({
    required this.playerID,
  });

  ///
  /// Convert object to toString
  ///
  @override
  String toString() {
    return 'FetchAllObjectiveRequest(playerID: $playerID)';
  }

  ///
  /// Get properites for comparisons
  @override
  List<Object?> get props => [
        playerID,
      ];

  ///
  /// Convert object to JSON
  JSON get asJson => {
        'playerID': playerID,
      };
}
