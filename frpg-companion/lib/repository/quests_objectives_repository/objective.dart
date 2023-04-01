import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';

class Objective extends Equatable {
  final int questID;
  final int objectiveID;
  final String description;

  const Objective(
      {required this.questID,
      required this.objectiveID,
      required this.description});

  @override
  List<Object?> get props => [questID, objectiveID, description];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {
      'questID': questID,
      'objectiveID': objectiveID,
      'description': description
    };
  }

  factory Objective.fromJson({
    required JSON json,
  }) {
    return Objective(
      questID: json['questID'],
      objectiveID: json['objectiveID'],
      description: json['description']
    );
  }
}
