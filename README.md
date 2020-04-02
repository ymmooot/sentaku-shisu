# sentaku-shisu

Notify Slack and Discord of forecast how dry the laundry.

## Usage

Set the env variables.  
[direnv](https://github.com/direnv/direnv) will help you.

| Key              | Description                                  | Note       |
| ---------------- | -------------------------------------------- | ---------- |
| AREA_CODE        | [tenki.jp](https://tenki.jp/) area code      |
| SEND_URL         | Webhook endpoint of Slack or Discord         | Use [Slack-Compatible webhook](https://discordapp.com/developers/docs/resources/webhook#execute-slackcompatible-webhook) to post to Discord. |

```bash
# run
$ ./gradlew run 

# test
$ ./gradlew test
```
