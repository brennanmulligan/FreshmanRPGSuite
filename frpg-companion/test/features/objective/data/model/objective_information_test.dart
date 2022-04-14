import 'package:flutter_test/flutter_test.dart';
import 'package:frpg_companion/features/objective/objective.dart';

void main() {
  group('Test ObjectiveInformation', () {
    test('Test creation', () {
      const actual = ObjectiveInformation(
          description: "description", questID: 1, objectiveID: 1);

      expect(actual.description, "description");
      expect(actual.objectiveID, 1);
      expect(actual.questID, 1);
    });

    test('Test comparisons', () {
      const instance1 =
          ObjectiveInformation(description: "Same", questID: 1, objectiveID: 1);

      const instance2 =
          ObjectiveInformation(description: "Same", questID: 1, objectiveID: 1);

      const instance3 = ObjectiveInformation(
          description: "different", questID: 6, objectiveID: 4);

      // Same
      expect(instance1.props, instance2.props);

      // Different
      expect(instance1.props, isNot(instance3.props));
      expect(instance2.props, isNot(instance3.props));
    });
  });
}
