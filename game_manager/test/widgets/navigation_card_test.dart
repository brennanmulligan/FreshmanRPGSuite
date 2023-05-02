import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:game_manager/pages/create_player/create_player_page.dart';
import 'package:game_manager/pages/dashboard/widgets/navigation_card.dart';

Widget createWidgetUnderTest() {
  return const MaterialApp(
    home: NavigationCard(
      cardTitle: 'Create Player',
      cardIcon: Icon(Icons.accessibility_new_rounded),
      cardLink: CreatePlayerPage(),
    ),
  );
}

void main() {
  testWidgets('Navigation Card Test', (WidgetTester tester) async {
    await tester.pumpWidget(createWidgetUnderTest());
    expect(find.text('Create Player'), findsOneWidget);
    expect(find.byIcon(Icons.accessibility_new_rounded), findsOneWidget);
  });
}
