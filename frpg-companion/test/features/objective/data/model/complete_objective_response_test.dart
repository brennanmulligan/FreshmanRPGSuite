import 'dart:convert';

import 'package:flutter_test/flutter_test.dart';
import 'package:frpg_companion/features/objective/objective.dart';

void main() {
  group('Test CompleteObjectiveResponse', () {
    test('Test creation.', () {
      const completed = CompleteObjectiveResponse(
        responseType: ObjectiveResponseType.completed,
      );

      expect(completed.responseType, ObjectiveResponseType.completed);

      const alreadyCompleted = CompleteObjectiveResponse(
        responseType: ObjectiveResponseType.alreadyCompleted,
      );

      expect(alreadyCompleted.responseType,
          ObjectiveResponseType.alreadyCompleted);

      const objectiveNotValid = CompleteObjectiveResponse(
        responseType: ObjectiveResponseType.objectiveNotValid,
      );

      expect(objectiveNotValid.responseType,
          ObjectiveResponseType.objectiveNotValid);

      const outOfRange = CompleteObjectiveResponse(
        responseType: ObjectiveResponseType.outOfRange,
      );

      expect(outOfRange.responseType, ObjectiveResponseType.outOfRange);

      const questNotValid = CompleteObjectiveResponse(
        responseType: ObjectiveResponseType.questNotValid,
      );

      expect(questNotValid.responseType, ObjectiveResponseType.questNotValid);

      const networkFailure = CompleteObjectiveResponse(
        responseType: ObjectiveResponseType.networkFailure,
      );

      expect(networkFailure.responseType, ObjectiveResponseType.networkFailure);
    });

    test('Test comparisons.', () {
      const completed1 = CompleteObjectiveResponse(
        responseType: ObjectiveResponseType.completed,
      );

      expect(completed1.responseType, ObjectiveResponseType.completed);

      const completed2 = CompleteObjectiveResponse(
        responseType: ObjectiveResponseType.completed,
      );

      expect(completed2.responseType, ObjectiveResponseType.completed);
      expect(completed1.props, completed2.props);

      const completed3 = CompleteObjectiveResponse(
        responseType: ObjectiveResponseType.alreadyCompleted,
      );

      expect(completed3.responseType, ObjectiveResponseType.alreadyCompleted);
      expect(completed1.props, isNot(completed3.props));
      expect(completed2.props, isNot(completed3.props));
    });

    test('Test fromJson factory.', () {
      final testJson = jsonDecode('''{
        "responseType": 0
        }''');

      const expected = CompleteObjectiveResponse(
        responseType: ObjectiveResponseType.completed,
      );

      final actual = CompleteObjectiveResponse.fromJson(json: testJson);

      expect(actual, expected);
    });
  });
}
