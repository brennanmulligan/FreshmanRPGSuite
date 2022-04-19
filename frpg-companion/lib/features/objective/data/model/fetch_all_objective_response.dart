import 'package:equatable/equatable.dart';
import 'package:frpg_companion/features/objective/data/data.dart';
import 'package:frpg_networking_api/networking/service_client/type_definitions/type_definitions.dart';

class FetchAllObjectiveResponse extends Equatable {
  final List<ObjectiveInformation> information;

  const FetchAllObjectiveResponse({
    required this.information,
  });

  factory FetchAllObjectiveResponse.fromJson({
    required JSON json,
  }) {
    return FetchAllObjectiveResponse(
      information: (json["objectives"] as List)
          .map(
            (objectiveJSON) => ObjectiveInformation.fromJson(
              json: objectiveJSON,
            ),
          )
          .toList(),
    );
  }

  @override
  List<Object?> get props => [information];
}
