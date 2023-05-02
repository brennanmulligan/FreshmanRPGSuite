import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:game_manager/pages/shared/widgets/notification_card.dart';

Widget createWidgetSuccess() {
  return const MaterialApp(
      home: NotificationCard(
        cardTitle: 'Success',
        description: 'This action was executed successfully.',
        success: true,
        color: Colors.green,
  ));
}

Widget createWidgetError() {
  return const MaterialApp(
      home: NotificationCard(
        cardTitle: 'Error',
        description: 'This action was not executed successfully.',
        success: false,
        color: Colors.red,
  ));
}

void main() {
  testWidgets('NotificationCard has an success message message', (WidgetTester tester) async {
    await tester.pumpWidget(createWidgetSuccess());
    expect(find.text('Success'), findsOneWidget);
    expect(find.text('This action was executed successfully.'), findsOneWidget);
    expect(find.byIcon(Icons.check), findsOneWidget);
    expect((tester.firstWidget(find.byType(NotificationCard)) as NotificationCard).color, Colors.green);
  });

  testWidgets('NotificationCard has an error message message', (WidgetTester tester) async {
    await tester.pumpWidget(createWidgetError());
    expect(find.text('Error'), findsOneWidget);
    expect(find.text('This action was not executed successfully.'), findsOneWidget);
    expect(find.byIcon(Icons.warning), findsOneWidget);
    expect((tester.firstWidget(find.byType(NotificationCard)) as NotificationCard).color, Colors.red);
  });
}
