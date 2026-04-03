## 🧩 Process Activity - Record Definitions to JSON
Написать Process Activity которая будет выводить все записи и все их поля из Record definition'а строкой в виде JSON массива:
- Настроить среду разработки согласно инструкциям из [README.MD](README.MD).
- Написать соотв. Java код.
- Выполнить успешный деплой в среду.
- Создать новый Process который будет выполнять новую Activity. После деплоя Activity должна появиться в палитре в левой части редактора процессов.
- Вывод должен происходить в Activity Result. Достаточно того чтобы JSON массив был виден в логе выполнения процесса.
- В качестве источника данных можно брать любой из Record definition'ов в текущем приложении (library), предварительно добавив в него несколько записей.

Примеры создания Process Activity и прочих компонентов можно найти в репозитории вендора:
- https://github.com/bmchelix/innovation-studio-developer/blob/main/23.3.02/test210500/README.MD

Там же есть пример Activity которая работает с Record Definition:
- https://github.com/bmchelix/innovation-studio-developer/blob/main/23.3.02/test210500/_details/JAVA.MD#AccessRecordInstances

Также, для данного задания может быть полезно изучить следующие примеры:
- https://github.com/bmchelix/innovation-studio-developer/blob/main/23.3/test210500/bundle/src/main/java/com/example/bundle/SimpleProcessActivity.java
- https://github.com/bmchelix/innovation-studio-developer/blob/main/23.3/test210500/bundle/src/main/java/com/example/bundle/Test210500FruitDataPageQuery.java