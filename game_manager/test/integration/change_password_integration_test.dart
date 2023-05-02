import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:game_manager/pages/change_password/change_password_page.dart';
import 'package:game_manager/pages/dashboard/dashboard_view.dart';
import 'package:game_manager/pages/dashboard/widgets/navigation_card.dart';
import 'package:game_manager/pages/shared/widgets/password.dart';
import 'package:game_manager/repository/player/all_players_request.dart';
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
    expect(find.byType(DropdownButtonFormField<String>), findsOneWidget);

    // These finders must use <String> despite DropdownButtonFormField defaulting to
    // <dynamic> since flutter test can't infer the type (most likely a bug)
    // View https://github.com/flutter/flutter/issues/119393 for more info
    await tester.ensureVisible(find.byType(DropdownMenuItem<String>).first);

    // Tap the first DropdownMenuItem which selects the first player
    await tester.tap(find.byType(DropdownMenuItem<String>).first,
        warnIfMissed: false);
    await tester.pumpAndSettle();

    // Tap the first option in the now opened dropdown
    await tester.ensureVisible(find.byType(DropdownButtonFormField<String>));
    await tester.tap(find.byType(DropdownButtonFormField<String>));
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
