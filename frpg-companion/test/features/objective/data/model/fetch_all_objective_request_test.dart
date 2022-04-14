import 'package:flutter_test/flutter_test.dart';
import 'package:frpg_companion/features/objective/objective.dart';

void main() {
  group('Test FetchAllObjectiveRequest', () {
    test('Test creation', () {
      const actual = FetchAllObjectiveRequest(
        playerID: 1,
      );

      expect(actual.playerID, 1);
    });

    test('Test comparisons', () {
      const instance1 = FetchAllObjectiveRequest(
        playerID: 1,
      );

      expect(instance1.playerID, 1);

      const instance2 = FetchAllObjectiveRequest(
        playerID: 1,
      );

      expect(instance2.playerID, 1);

      expect(instance1.props, instance2.props);

      const instance3 = FetchAllObjectiveRequest(
        playerID: 9,
      );

      expect(instance3.playerID, 9);

      expect(instance1.props, isNot(instance3.props));
      expect(instance2.props, isNot(instance3.props));
    });
  });
}
