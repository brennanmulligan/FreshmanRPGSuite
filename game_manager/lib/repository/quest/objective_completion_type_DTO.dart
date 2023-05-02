import 'package:equatable/equatable.dart';
import '../../type_definitions.dart';

class ObjectiveCompletionTypeDTO extends Equatable {
  final String objCompletionName;
  final int objCompletionId;

  const ObjectiveCompletionTypeDTO({required this.objCompletionName, required this.objCompletionId});

  const ObjectiveCompletionTypeDTO.allFields({required this.objCompletionName, required this.objCompletionId});

  @override
  List<Object?> get props => [objCompletionName, objCompletionId];

  ///
  /// Convert object to JSON.
  ///
  Map<String, dynamic> toJson() {
    return {
      'objCompletionName': objCompletionName,
      'objCompletionId' : objCompletionId,
    };
  }

  factory ObjectiveCompletionTypeDTO.fromJson({
    required JSON json,
  }) {
    return ObjectiveCompletionTypeDTO.allFields(
        objCompletionName: json['objCompletionName'],
        objCompletionId: json['objCompletionId']
    );
  }

}