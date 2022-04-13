import 'package:flutter_test/flutter_test.dart';
import 'package:frpg_companion/features/objective/objective.dart';

void main() {
  group('Test CompleteObjectiveRequest', () {
    test('Test creation', () {
      const actual = CompleteObjectiveRequest(
        playerID: 1,
        objectiveID: 2,
        questID: 3,
      );

      expect(actual.playerID, 1);
      expect(actual.objectiveID, 2);
      expect(actual.questID, 3);
      expect(actual.latitude, 0);
      expect(actual.longitude, 0);
    });

    test('Test comparisons', () {
      const instance1 = CompleteObjectiveRequest(
        playerID: 1,
        objectiveID: 2,
        questID: 3,
      );

      expect(instance1.playerID, 1);
      expect(instance1.objectiveID, 2);
      expect(instance1.questID, 3);
      expect(instance1.latitude, 0);
      expect(instance1.longitude, 0);

      const instance2 = CompleteObjectiveRequest(
        playerID: 1,
        objectiveID: 2,
        questID: 3,
      );

      expect(instance2.playerID, 1);
      expect(instance2.objectiveID, 2);
      expect(instance2.questID, 3);
      expect(instance2.latitude, 0);
      expect(instance2.longitude, 0);

      expect(instance1.props, instance2.props);

      const instance3 = CompleteObjectiveRequest(
        playerID: 9,
        objectiveID: 9,
        questID: 9,
      );

      expect(instance3.playerID, 9);
      expect(instance3.objectiveID, 9);
      expect(instance3.questID, 9);
      expect(instance3.latitude, 0);
      expect(instance3.longitude, 0);

      expect(instance1.props, isNot(instance3.props));
      expect(instance2.props, isNot(instance3.props));
    });
  });
}
