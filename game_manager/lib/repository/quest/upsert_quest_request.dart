import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';

class UpsertQuestRequest extends Equatable {
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
      this.title,
      this.description,
      this.xpGained,
      this.triggerMapName,
      this.triggerRow,
      this.triggerCol,
      this.objectivesForFulfillment,
      this.completionActionType,
      this.startDate,
      this.endDate,
      this.easterEgg);

  @override
  List<Object> get props => [
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
    return 'UpsertQuestRequest(title: $title, description: $description, xpGained: $xpGained, triggerMapName: $triggerMapName, triggerRow: $triggerRow, triggerCol: $triggerCol, objectivesForFulfillment: $objectivesForFulfillment, completionActionType: $completionActionType, startDate: $startDate, endDate: $endDate, easterEgg: $easterEgg)';
  }

  JSON get asJson => {
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