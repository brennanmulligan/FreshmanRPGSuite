import 'dart:convert';

import 'package:flutter_test/flutter_test.dart';
import 'package:frpg_companion/features/objective/objective.dart';

void main() {
  group('Test FetchAllObjectiveResponse', () {
    test('Test creation.', () {
      const objective1 = ObjectiveInformation(
        description: "One",
        questID: 1,
        objectiveID: 1,
      );

      const objective2 = ObjectiveInformation(
        description: "Two",
        questID: 3,
        objectiveID: 1,
      );

      const objective3 = ObjectiveInformation(
        description: "Three",
        questID: 6,
        objectiveID: 4,
      );

      const instance1 = FetchAllObjectiveResponse(information: [
        objective1,
        objective2,
        objective3,
      ]);
      expect(instance1.information, [
        objective1,
        objective2,
        objective3,
      ]);
    });

    test('Test comparisons.', () {
      const objective1 = ObjectiveInformation(
        description: "One",
        questID: 1,
        objectiveID: 1,
      );

      const objective2 = ObjectiveInformation(
        description: "Two",
        questID: 3,
        objectiveID: 1,
      );

      const objective3 = ObjectiveInformation(
        description: "Three",
        questID: 6,
        objectiveID: 4,
      );

      const instance1 = FetchAllObjectiveResponse(information: [
        objective1,
        objective2,
        objective3,
      ]);

      const instance2 = FetchAllObjectiveResponse(information: [
        objective1,
        objective2,
        objective3,
      ]);

      const instance3 = FetchAllObjectiveResponse(information: [
        objective3,
        objective2,
        objective1,
      ]);

      expect(instance1.props, instance2.props);

      expect(instance1.props, isNot(instance3.props));
      expect(instance2.props, isNot(instance3.props));
    });

    test('Test fromJson factory.', () {
      final testJson = jsonDecode('''{
        "objectives": [
          {
            "description": "funny",
            "questID": 1,
            "objectiveID": 2
          },
          {
            "description": "bird",
            "questID": 3,
            "objectiveID": 4
          },
          {
            "description": "app",
            "questID": 5,
            "objectiveID": 6
          }
        ]
      }''');

      const objective1 = ObjectiveInformation(
        description: "funny",
        questID: 1,
        objectiveID: 2,
      );

      const objective2 = ObjectiveInformation(
        description: "bird",
        questID: 3,
        objectiveID: 4,
      );

      const objective3 = ObjectiveInformation(
        description: "app",
        questID: 5,
        objectiveID: 6,
      );

      const expected = FetchAllObjectiveResponse(information: [
        objective1,
        objective2,
        objective3,
      ]);

      final actual = FetchAllObjectiveResponse.fromJson(json: testJson);
      expect(actual, expected);
    });
  });
}
