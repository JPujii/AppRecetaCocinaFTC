import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  @Input() sideNavStatus: boolean = false;

  list = [
    {
      number: '1',
      name: 'Usuarios',
      icon: 'fa-solid fa-user'
    },
    {
      number: '2',
      name: 'Recetas',
      icon: 'fa-solid fa-book'
    },
  ]

  constructor() { }

  ngOnInit(): void { }

}