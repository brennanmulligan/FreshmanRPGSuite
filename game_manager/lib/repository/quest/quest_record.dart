import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';
import 'action_type_DTO.dart';

class QuestRecord extends Equatable {
  final int id;
  final String title;
  final String description;
  final int xpGained;
  final String triggerMapName;
  final int triggerRow;
  final int triggerCol;
  final int objectivesForFulfillment;
  final ActionTypeDTO completionActionType;
  final String startDate;
  final String endDate;
  final bool easterEgg;

  const QuestRecord(
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
  List<Object?> get props => [id, title, description, xpGained, triggerMapName, triggerRow, triggerCol,
                              objectivesForFulfillment, completionActionType, startDate, endDate, easterEgg];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'title': title,
      'description' : description,
      'xpGained' : xpGained,
      'triggerMapName' : triggerMapName,
      'triggerRow' : triggerRow,
      'triggerCol' : triggerCol,
      'objectivesForFulfillment': objectivesForFulfillment,
      'completionActionType' : completionActionType,
      'startDate' : startDate,
      'endDate' : endDate,
      'easterEgg': easterEgg
    };
  }

  factory QuestRecord.fromJson({
    required JSON json,
  }) {
    return QuestRecord(
      id: json['id'],
      title: json['title'],
      description: json['description'],
      xpGained : json['xpGained'],
      triggerMapName : json['triggerMapName'],
      triggerRow : json['triggerRow'],
      triggerCol : json['triggerCol'],
      objectivesForFulfillment : json['objectivesForFulfillment'],
      completionActionType : ActionTypeDTO.fromJson(json: json['completionActionType']),
      startDate : json['startDate'],
      endDate : json['endDate'],
      easterEgg : json['easterEgg']
    );
  }
}

