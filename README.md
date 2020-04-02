# sentaku-shisu

Notify Slack and Discord of forecast how dry the laundry.

## Usage

Set the env variables.  
[direnv](https://github.com/direnv/direnv) will help you.

| Key              | Note                                         |
| ---------------- | -------------------------------------------- |
| AREA_CODE        | [tenki.jp](https://tenki.jp/) area code      |
| SEND_URL         | endpoint url of Discord or Slack             |

```bash
# run
$ ./gradlew run 

# test
$ ./gradlew test
```
