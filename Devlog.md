## 2021/10/2 Development Log

Currently, I implement some basic functions of the *Chit Chat* app, including login, register, logout, verify users' login status.

### Login & Register

The app basically uses firebase -> *password authentication* to allow users to login and register. As a attempter, users need to input three basic information: email, username, password. If users do not fill all three fields, the error will happen. **However**, this part now only is a prototype, because it is not the same as the design which we did previously. It is just a attempt.  There are still several function need to be added.

1. Regular expression has to be implemented. When users input their email, username, password, the app need to verify whether formats are correct or not. For instance, username should not exceed fixed length, password must contain numbers and alphabet, email's format must be correct.
2. The origin idea of the previous design is to let users input password and username during register period. But it is not implementation in this time. **How to combine randomly assigned account and email registration is a serious problem. And now I have no idea.**
3. Email verification is a great idea, and it is not very difficult to accomplish.
4. The UI design and overall design may need to modify.

---

### Verify users' login status

Once users enter into MainActivity successfully, the app will remember it. After login in, users need to click menu -> logout to logout.

---

### UI Design

A pretty rough UI prototype has been implemented. UI group can reference it, but please do not copy it directly because it is freaking ugly.






