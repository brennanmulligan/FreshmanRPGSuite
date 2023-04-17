import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';

class UpsertQuestRequest extends Equatable {
  final int id;
  final String title;
  final String description;
  final int xpGained;
  final String triggerMapName;
  final int triggerRow;
  final int triggerCol;
  final int objectivesForFulfillment;
  final int completionActionType;
  final String startDate;
  final String endDate;
  final bool easterEgg;

  const UpsertQuestRequest(
  { required this.id,
    required this.title,
    required this.description,
    required this.xpGained,
    required this.triggerMapName,
    required this.triggerRow,
    required this.triggerCol,
    required this.objectivesForFulfillment,
    required this.completionActionType,
    required this.startDate,
    required this.endDate,
    required this.easterEgg});

  @override
  List<Object> get props => [
        id,
        title,
        description,
        xpGained,
        triggerMapName,
        triggerRow,
        triggerCol,
        objectivesForFulfillment,
        completionActionType,
        startDate,
        endDate,
        easterEgg
      ];

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'description': description,
      'xpGained': xpGained,
      'triggerMapName': triggerMapName,
      'triggerRow': triggerRow,
      'triggerCol': triggerCol,
      'objectivesForFulfillment': objectivesForFulfillment,
      'completionActionType': completionActionType,
      'startDate': startDate,
      'endDate': endDate,
      'easterEgg': easterEgg
    };
  }

  @override
  String toString() {
    return 'UpsertQuestRequest(id: $id, title: $title, description: $description, xpGained: $xpGained, triggerMapName: $triggerMapName, triggerRow: $triggerRow, triggerCol: $triggerCol, objectivesForFulfillment: $objectivesForFulfillment, completionActionType: $completionActionType, startDate: $startDate, endDate: $endDate, easterEgg: $easterEgg)';
  }

  JSON get asJson => {
        'id': id,
        'title': title,
        'description': description,
        'xpGained': xpGained,
        'triggerMapName': triggerMapName,
        'triggerRow': triggerRow,
        'triggerCol': triggerCol,
        'objectivesForFulfillment': objectivesForFulfillment,
        'completionActionType': completionActionType,
        'startDate': startDate,
        'endDate': endDate,
        'easterEgg': easterEgg
      };
}
