import 'package:equatable/equatable.dart';

import '../../type_definitions.dart';

class ActionTypeDTO extends Equatable {
  final String actionName;
  final int actionID;

  const ActionTypeDTO({required this.actionName, required this.actionID});

  const ActionTypeDTO.allFields({required this.actionName, required this.actionID});

  @override
  List<Object?> get props => [actionName, actionID];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {
      'actionName': actionName,
      'actionID' : actionID,
    };
  }

  factory ActionTypeDTO.fromJson({
    required JSON json,
  }) {
    return ActionTypeDTO.allFields(
      actionName: json['actionName'],
      actionID: json['actionID']
    );
  }
}