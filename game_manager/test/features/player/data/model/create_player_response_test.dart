import 'dart:convert';

import 'package:test/test.dart';
import 'package:game_manager/features/player/player.dart';

void main() {
  group('Test CreatePlayerResponse', () {
    test('Test creation.', () {
      const created = CreatePlayerResponse(
          success: true,
          description: "desc"
      );

      expect(true, created.success);
      expect("desc", created.description);
    });


    test('Test fromJson factory.', () {
      final testJson = jsonDecode('''{
        "success": true,
        "description": "desc"
        }''');
      
      const actual = CreatePlayerResponse(
          success: true,
          description: "desc"
      );

      final expected = CreatePlayerResponse.fromJson(json: testJson);
      expect(expected, actual);
    });

});
}