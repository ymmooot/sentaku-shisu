# sentaku-shisu

Notify Slack and Discord of forecast how dry the laundry.

## Usage

Set the env variables.  
[direnv](https://github.com/direnv/direnv) will help you.

| Key              | Required | Description                                  | Note       |
| ---------------- | -------- | -------------------------------------------- | ---------- |
| AREA_CODE        | true     | [tenki.jp](https://tenki.jp/) area code      |            |
| ENDPOINT_URL     | true     | Webhook endpoint of Slack or Discord         | Use [Slack-Compatible webhook](https://discordapp.com/developers/docs/resources/webhook#execute-slackcompatible-webhook) to post to Discord. |
| ICON_URL         | false    | Icon URL of this bot                         |            |

```bash
# run
$ ./gradlew run 

# test
$ ./gradlew test
```
