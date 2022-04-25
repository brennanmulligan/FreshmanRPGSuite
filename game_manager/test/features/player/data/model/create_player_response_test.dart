import 'dart:convert';

import 'package:test/test.dart';
import 'package:game_manager/features/player/player.dart';

void main() {
  group('Test CreatePlayerResonse', () {
    test('Test creation.', () {
      const created = CreatePlayerResponse(
        responseType: PlayerResponseType.created,
      );

      expect(created.responseType, PlayerResponseType.created);
      
      const alreadyExists = CreatePlayerResponse(
        responseType: PlayerResponseType.alreadyExists,
      );

      expect(alreadyExists.responseType, PlayerResponseType.alreadyExists);
      
      const crewNotValid = CreatePlayerResponse(
        responseType: PlayerResponseType.crewNotValid,
      );

      expect(crewNotValid.responseType, PlayerResponseType.crewNotValid);

      const majorNotValid = CreatePlayerResponse(
        responseType: PlayerResponseType.majorNotValid,
      );

      expect(majorNotValid.responseType, PlayerResponseType.majorNotValid);

      const sectionNotValid = CreatePlayerResponse(
        responseType: PlayerResponseType.sectionNotValid,
      );

      expect(sectionNotValid.responseType, PlayerResponseType.sectionNotValid);

      const networkFailure = CreatePlayerResponse(
        responseType: PlayerResponseType.networkFailure,
      );

      expect(networkFailure.responseType, PlayerResponseType.networkFailure);
    });

    test('Test comparisons.', () {
      const completed1 = CreatePlayerResponse(
        responseType: PlayerResponseType.created
      );

      expect(completed1.responseType, PlayerResponseType.created);

      const completed2 = CreatePlayerResponse(
        responseType: PlayerResponseType.created
      );

      expect(completed2.responseType, PlayerResponseType.created);
      expect(completed1.props, completed2.props);

      const alreadyExists = CreatePlayerResponse(
        responseType: PlayerResponseType.alreadyExists
      );

      expect(alreadyExists.responseType, PlayerResponseType.alreadyExists);
      expect(completed1.props, isNot(alreadyExists.props));
      expect(completed2.props, isNot(alreadyExists.props));

    });

    test('Test fromJson factory.', () {
      final testJson = jsonDecode('''{
        "responseType": 0
        }''');
      
      const expected = CreatePlayerResponse(
        responseType: PlayerResponseType.created,
      );

      final actual = CreatePlayerResponse.fromJson(json: testJson);

      expect(actual, expected);
    });
  });
}