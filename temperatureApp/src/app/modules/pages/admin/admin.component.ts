import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../shared/services/auth/auth.service';
import { UserService, User } from '../../shared/services/user/user.service';
import { isObject } from 'util';

@Component({
  selector: 'app-admin',
  providers: [UserService],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  private listUsers: User[];
  private user: Object;
  private showAddButton: Boolean;
  private showAddForm: Boolean;
  private showEditForm: Boolean;
  private showList: Boolean;
  private showAlertAdd: Boolean;
  private showAlertEdit: Boolean;
  private newUser = { name: '', surn: '', email: '', pass: '' };
  private edit = { id: '', name: '', surn: '', email: '', pass: '' };

  constructor(public authService: AuthService, public router: Router, private userService: UserService) {
    this.showAddButton = true;
    this.showEditForm = false;
    this.showList = true;
    this.showAlertAdd = false;
    this.showAlertEdit = false;
  }

  ngOnInit() {
    this.userService.getUser().subscribe(data => {
      this.listUsers = data;
    });
    setInterval(() => {
      this.userService.getUser().subscribe(data => {
        this.listUsers = data;
      });
    }, 1000);
  }

  displayAddForm() {
    this.newUser = { name: '', surn: '', email: '', pass: '' };
    this.showAlertAdd = false;
    this.showAddButton = false;
    this.showList = false;
    this.showAddForm = true;
  }

  addUser() {
    this.userService.addUser(this.newUser).subscribe(bool => {
      if (bool) {
        this.showAddForm = false;
        this.showAddButton = true;
        this.showList = true;
        this.showAlertAdd = false;
      } else {
        this.showAlertAdd = true;
      }
    });
  }

  deleteUser(id) {
    this.userService.delUser(id).subscribe(data => {
      this.user = data;
    });
  }

  setUser(id) {
    this.userService.setUser(id).subscribe(data => {
      this.user = data;
    });
  }

  deactivateUser(id) {
    this.userService.deactivateUser(id).subscribe(data => {
      this.user = data;
    });
  }
  activateUser(id) {
    this.userService.activateUser(id).subscribe(data => {
      this.user = data;
    });
  }

  setAdmin(id) {
    this.userService.setAdmin(id).subscribe(data => {
      this.user = data;
    });
  }

  displayEditForm(user) {
    this.edit.id = user.id;
    this.edit.name = user.name;
    this.edit.surn = user.surn;
    this.edit.email = user.email;
    this.edit.pass = user.pass;
    this.showAlertEdit = false;
    this.showAddButton = false;
    this.showEditForm = true;
    this.showList = false;
  }

  editUser() {
    this.userService.editUser(this.edit).subscribe(bool => {
      if (bool) {
        this.showEditForm = false;
        this.showAddButton = true;
        this.showList = true;
        this.showAlertEdit = false;
      } else {
        this.showAlertEdit = true;
      }
    });
  }

  cancelAdd() {
    this.showAddButton = true;
    this.showAddForm = false;
    this.showList = true;
  }

  cancelEdit() {
    this.showAddButton = true;
    this.showEditForm = false;
    this.showList = true;
  }
}
