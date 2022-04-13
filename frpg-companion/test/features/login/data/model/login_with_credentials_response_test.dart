import 'dart:convert';

import 'package:flutter_test/flutter_test.dart';
import 'package:frpg_companion/features/login/login.dart';

void main() {
  group('Test LoginWithCredentialsResponse', () {
    test('Test creation.', () {
      const valid = LoginWithCredentialsResponse(
        playerID: 1
      );

      expect(valid.playerID, 1);

      const invalid = LoginWithCredentialsResponse(
        playerID: -1
      );

      expect(invalid.playerID, -1);

    });

    test('Test comparisons.', () {
      const valid1 = LoginWithCredentialsResponse(
        playerID: 1
      );

      expect(valid1.playerID, 1);

      const valid2 = LoginWithCredentialsResponse(
        playerID: 1
      );

      expect(valid2.playerID, 1);
      expect(valid1.props, valid1.props);

      const invalid = LoginWithCredentialsResponse(
        playerID: -1
      );

      expect(invalid.playerID, -1);
      expect(valid1.props, isNot(invalid.props));
      expect(valid2.props, isNot(invalid.props));
    });

    test('Test fromJson factory.', () {
      final testJson = jsonDecode('''{
        "playerID": 1
        }''');

      const expected = LoginWithCredentialsResponse(
        playerID: 1,
      );

      final actual = LoginWithCredentialsResponse.fromJson(json: testJson);

      expect(actual, expected);
    });
  });
}
