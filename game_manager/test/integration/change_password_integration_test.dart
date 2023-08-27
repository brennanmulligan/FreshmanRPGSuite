import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:game_manager/pages/change_password/change_password_page.dart';
import 'package:game_manager/pages/shared/widgets/password.dart';
import 'package:game_manager/repository/player/all_players_response.dart';
import 'package:game_manager/repository/player/player.dart';
import 'package:game_manager/repository/player/player_repository.dart';
import 'package:mockito/annotations.dart';
import 'package:mockito/mockito.dart';

import '../pages/change_password/change_password_test.mocks.dart';

class PlayerRepositoryTest extends Mock implements PlayerRepository {}

@GenerateMocks([PlayerRepositoryTest])
Future<void> main() async{
  late MockPlayerRepositoryTest mockPlayerRepo;

  setUpAll(() {
    mockPlayerRepo = MockPlayerRepositoryTest();
  });

  testWidgets('Change Password Flow', (WidgetTester tester) async {
    // Create our mock response
    AllPlayersResponse mockAllPlayersResponse = AllPlayersResponse(
        true,
        players: [
          Player(
              playerName: "test",
              playerID: 1
          )
        ]);

    // When we call getAllPlayers, return our mock response
    when(mockPlayerRepo.getAllPlayers(any))
        .thenAnswer((_) async => mockAllPlayersResponse);

    // Generate the widget tree with our mock repository
    await tester.pumpWidget(
          MaterialApp(
            home: ChangePasswordPage(playerRepository: mockPlayerRepo),
          ),
    );

    await tester.pumpAndSettle();

    // Ensure the page is built
    expect(find.byType(ChangePasswordPage), findsOneWidget);

    // Ensure the submit button is visible
    expect(find.byType(ElevatedButton).first, findsOneWidget);

    // Ensure the elevated button onPressed is null since the password fields are empty and no player is selected
    expect((tester.firstWidget(find.byType(ElevatedButton)) as ElevatedButton).onPressed, isNull);
    await tester.pumpAndSettle();

    // Find the DropdownButtonFormField Widget, and fill it with test data
    var dropDown = find.byType(DropdownButtonFormField<String>);
    expect(dropDown, findsOneWidget);

    // open the dropdown
    await tester.tap(dropDown);
    await tester.pumpAndSettle();

    print(dropDown.first.toString());

    // click on Merlin
    final dropdownItem = find.text('test');
    await tester.tap(dropdownItem);
    await tester.pumpAndSettle();

    // Ensure the password field is visible
    expect(find.byType(PasswordField), findsOneWidget);

    await tester.enterText(find.byType(PasswordField).first, "GoodPassword123!");
    await tester.pumpAndSettle();

    // Ensure the confirm password field is visible
    // There are multiple since PasswordField is a wrapper for TextField
    expect(find.byType(TextField), findsWidgets);

    // We need the confirm password field, so we use the second TextField
    await tester.enterText(find.byType(TextField).at(1), "GoodPassword123!");
    await tester.pumpAndSettle();

    // Check to see if the onPressed function is null in the ElevatedButton
    expect(
        (tester.firstWidget(find.byType(ElevatedButton)) as ElevatedButton)
            .onPressed,
        isNotNull);
  });
}
