import 'package:flutter_test/flutter_test.dart';
import 'package:game_manager/features/player/player.dart';

void main() {
  group('Test CreatePlayerRequest', () {
    test('Test creation', () {
      const actual = CreatePlayerRequest(
        name: "boby", 
        password: "bbbb123", 
        crew: 1, 
        major: 1, 
        section: 1,
      );
      
      expect(actual.name, "boby");
      expect(actual.password, "bbbb123");
      expect(actual.crew, 1);
      expect(actual.major, 1);
      expect(actual.section, 1);
    });

    test('Test comparison', () {
      const instance1 = CreatePlayerRequest(
        name: "boby", 
        password: "bbbb123", 
        crew: 1, 
        major: 1, 
        section: 1,
      );
      
      expect(instance1.name, "boby");
      expect(instance1.password, "bbbb123");
      expect(instance1.crew, 1);
      expect(instance1.major, 1);
      expect(instance1.section, 1);

      const instance2 = CreatePlayerRequest(
        name: "boby", 
        password: "bbbb123", 
        crew: 1, 
        major: 1, 
        section: 1,
      );
      
      expect(instance2.name, "boby");
      expect(instance2.password, "bbbb123");
      expect(instance2.crew, 1);
      expect(instance2.major, 1);
      expect(instance2.section, 1);

      expect(instance1.props, instance2.props);

      const instance3 = CreatePlayerRequest(
        name: "some dude", 
        password: "passha", 
        crew: 2, 
        major: 3, 
        section: 3,
      );
      
      expect(instance3.name, "some dude");
      expect(instance3.password, "passha");
      expect(instance3.crew, 2);
      expect(instance3.major, 3);
      expect(instance3.section, 3);

      expect(instance1, isNot(instance3.props));
      expect(instance2, isNot(instance3.props));
    });
    
  });
}