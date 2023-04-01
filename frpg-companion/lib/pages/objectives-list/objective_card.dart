import 'package:companion_app/pages/objectives-list/bloc/objectives_list_bloc.dart';
import 'package:flutter/material.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:hooks_riverpod/hooks_riverpod.dart';

import '../../repository/quests_objectives_repository/objective.dart';


class ObjectiveCard extends HookConsumerWidget {
  final Objective info;

  final int playerID;
  const ObjectiveCard({Key? key, required this.info, required this.playerID}) :
        super(key: key);

  @override
  Widget build(BuildContext context, WidgetRef ref) {


    return SafeArea(
      minimum: const EdgeInsets.only(left: 12, right: 12),
      child: Card(
        child: Row(
          mainAxisAlignment: MainAxisAlignment.start,
          children: <Widget>[
            Expanded(
              flex:2,
              child: Text(
                info.description,
                softWrap: true,
                style: const TextStyle(
                  fontSize: 16,
                  fontWeight: FontWeight.w500,
                ),
              ),
            ),
            const Spacer(),
            IconButton(
              tooltip: 'Open the QR code scanner.',
              onPressed: () => BlocProvider.of<ObjectivesListBloc>(context).add
                (RequestQRCodeScanEvent( playerID, info.questID, info.objectiveID)),
              icon: const Icon(
                Icons.qr_code,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
