import 'package:flutter_test/flutter_test.dart';
import 'package:frpg_companion/features/login/login.dart';

void main() {
  group('Test LoginWithCredentialsRequest', () {
    test('Test creation', () {
      const actual = LoginWithCredentialsRequest(
        username: "john",
        password: "pw",
      );

      expect(actual.username, "john");
      expect(actual.password, "pw");
    });
    test('Test comparisons', () {
      const instance1 = LoginWithCredentialsRequest(
        username: "john",
        password: "pw",
      );

      expect(instance1.username, "john");
      expect(instance1.password, "pw");

      const instance2 = LoginWithCredentialsRequest(
        username: "john",
        password: "pw",
      );

      expect(instance2.username, "john");
      expect(instance2.password, "pw");

      expect(instance1.props, instance2.props);

      const instance3 = LoginWithCredentialsRequest(
        username: "josh",
        password: "pw",
      );

      expect(instance3.username, "josh");
      expect(instance3.password, "pw");

      expect(instance1.props, isNot(instance3.props));
      expect(instance2.props, isNot(instance3.props));
    });
  });
}
